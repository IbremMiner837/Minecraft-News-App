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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.mcbedrock.minecraftnews.databinding.FragmentArticleBinding;
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

        // Create an English-RUSSIAN translator:
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.RUSSIAN)
                        .build();
        final Translator englishRussianTranslator =
                Translation.getClient(options);

        Translator translator = Translation.getClient(options);
        getLifecycle().addObserver(translator);
        binding.ImageView.setOnClickListener(view -> {
            englishRussianTranslator.translate(html)
                    .addOnSuccessListener(
                            (OnSuccessListener) o -> {
                                binding.TextView.setText("");
                                binding.TextView.append(Html.fromHtml((String) o));
                            })
                    .addOnFailureListener(
                            e -> {
                                // Error.
                                // ...
                            });
        });

        Bundle finalBundle = new Bundle();
        finalBundle.putAll(getArguments());
        String URL = finalBundle.getString("URL");

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