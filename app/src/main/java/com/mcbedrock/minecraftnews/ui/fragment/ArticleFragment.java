package com.mcbedrock.minecraftnews.ui.fragment;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.mcbedrock.minecraftnews.R;
import com.mcbedrock.minecraftnews.databinding.FragmentArticleBinding;
import com.mcbedrock.minecraftnews.utils.DialogsUtil;
import com.mcbedrock.minecraftnews.utils.SharedPreferencesUtil;
import com.mcbedrock.minecraftnews.utils.TranslationHelper;
import com.mcbedrock.minecraftnews.utils.ContentHelper;

import org.json.JSONException;

public class ArticleFragment extends Fragment {

    private FragmentArticleBinding binding;
    private String html;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArticleBinding.inflate(inflater, container, false);

        Bundle finalBundle = new Bundle();
        finalBundle.putAll(getArguments());
        String URL = finalBundle.getString("URL");

        if (SharedPreferencesUtil.getBooleanFromSharedPreferences(getContext(), "enable_translation")) {
            binding.translateBtn.setVisibility(View.VISIBLE);
        } else {
            binding.translateBtn.setVisibility(View.GONE);
        }

        if (!TranslationHelper.isLanguageDownloaded(TranslationHelper.getSystemLanguage())) {
            binding.translateBtn.setIcon(getContext().getDrawable(R.drawable.ic_round_error_outline_24));
            binding.translateBtn.setIconTint(getContext().getColorStateList(R.color.design_default_color_error));
            binding.translateBtn.setText(getContext().getString(R.string.model_not_downloaded));
            binding.translateBtn.setTextColor(getContext().getColor(R.color.design_default_color_error));
            //binding.translateBtn.setEnabled(false);

            binding.translateBtn.setOnClickListener(v -> {
                new DialogsUtil().downloadTranslateModel(getContext());
            });
        } else {
            binding.translateBtn.setOnClickListener(v -> {
                if (!TranslationHelper.isTranslated()) {
                    TranslationHelper.isTranslated = true;
                    TranslationHelper.translateArticle(html, binding.TextView, binding.translateBtn);
                } else {
                    TranslationHelper.isTranslated = false;
                    binding.translateBtn.setText(R.string.translate);
                    binding.TextView.setMovementMethod(LinkMovementMethod.getInstance());
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        binding.TextView.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY, null, null));
                    } else {
                        binding.TextView.setText(Html.fromHtml(html));
                    }
                }
            });
        }

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        html = response.getString("body");

                        binding.TextView.setMovementMethod(LinkMovementMethod.getInstance());
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            binding.TextView.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY, null, null));
                        } else {
                            binding.TextView.setText(Html.fromHtml(html));
                        }

                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions = requestOptions.transform(new CenterCrop());
                        Glide.with(this)
                                .load(ContentHelper.CONTENT + response.getJSONObject("image").getString("url"))
                                .apply(requestOptions)
                                .into(binding.ImageView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace
        );
        queue.add(jsonObjectRequest);
        
        return binding.getRoot();
    }
}