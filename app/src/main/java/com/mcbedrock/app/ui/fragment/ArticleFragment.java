package com.mcbedrock.app.ui.fragment;

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
import com.mcbedrock.app.Constants;
import com.mcbedrock.app.databinding.FragmentArticleBinding;
import com.mcbedrock.app.utils.TranslationHelper;

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
        TranslationHelper translationHelper = new TranslationHelper();

        Bundle finalBundle = new Bundle();
        finalBundle.putAll(getArguments());
        String URL = finalBundle.getString("URL");

        binding.translateBtn.setOnClickListener(v -> {
            String originalText = binding.TextView.getText().toString();
            String fromLanguage = "en";  // Assuming the original text is in English
            String toLanguage = "ru";    // Assuming you want to translate to Russian

            // Set up the translation callback to update the UI with translated text
            TranslationHelper.setTranslationCallback(translatedText -> {
                // Update the UI with the translated text
                getActivity().runOnUiThread(() -> {
                    binding.TextView.setText(translatedText);
                });
            });

            // Perform translation
            TranslationHelper.translate(originalText, fromLanguage, toLanguage);

        });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        html = response.getString("body");
                        binding.TextView.setMovementMethod(LinkMovementMethod.getInstance());
                        binding.TextView.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY, null, null));

                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions = requestOptions.transform(new CenterCrop());
                        Glide.with(this)
                                .load(Constants.CONTENT + response.getJSONObject("image").getString("url"))
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}