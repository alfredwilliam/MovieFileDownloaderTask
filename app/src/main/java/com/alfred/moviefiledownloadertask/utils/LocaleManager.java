package com.alfred.moviefiledownloadertask.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.blankj.utilcode.util.CacheDiskUtils;

import java.util.Locale;

import javax.inject.Inject;

public class LocaleManager {

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";
    private static final String DEFAULT_LANGUAGE = "en";
    private static String lang;
    @Inject
    MyApplication application;
    private Locale locale = null;

    @Inject
    public LocaleManager(MyApplication app) {
        this.application = app;
    }

    public void changeLang(String lang) {
        Configuration config = application.getResources().getConfiguration();
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {

            setLang(lang);
            locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration conf = new Configuration(config);
            conf.setLayoutDirection(locale);
            conf.locale = locale;

            application.getResources().updateConfiguration(conf, application.getResources().getDisplayMetrics());

            Intent i = application.getPackageManager()
                    .getLaunchIntentForPackage( application.getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            application.startActivity(i);

        }
    }

    public static void updateLanguage(Context context, String language) {
        final Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration cfg = new Configuration(res.getConfiguration());
        cfg.locale = locale;
        res.updateConfiguration(cfg, res.getDisplayMetrics());
    }

    public static String getLang() {
        lang = CacheDiskUtils.getInstance().getString(SELECTED_LANGUAGE);
        if(lang == null)
        {
            return "en_us";
        }
        return CacheDiskUtils.getInstance().getString(SELECTED_LANGUAGE);
    }

    public static void setLang(String language) {
        lang = language;
        CacheDiskUtils.getInstance().put(SELECTED_LANGUAGE,lang);
    }

//    public static Context onAttach(Context context){
//        String lang = getPersistedData(context,Locale.getDefault().getLanguage());
//        return setLocale(context,lang);
//    }
//
//    public static Context onAttach(Context context, String defaultLanguage){
//        String lang = getPersistedData(context,Locale.getDefault().getLanguage());
//        return setLocale(context,lang);
//    }
//
//    public static Context setLocale(Context context,String lang)
//    {
//        Persist(context,lang);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//            return updateResources(context,lang);
//        return updateResourcesLegacy(context,lang);
//    }
//
//    @TargetApi(Build.VERSION_CODES.N)
//    private static Context updateResources(Context context, String lang) {
//        Locale locale = new Locale(lang);
//        Locale.setDefault(locale);
//        Configuration config = context.getResources().getConfiguration();
//        config.setLocale(locale);
//        config.setLayoutDirection(locale);
//        return context.createConfigurationContext(config);
//    }
//
//    @SuppressWarnings("deprecation")
//    private static Context updateResourcesLegacy(Context context, String lang) {
//        Locale locale = new Locale(lang);
//        Locale.setDefault(locale);
//        Resources resources = context.getResources();
//        Configuration config = context.getResources().getConfiguration();
//        config.setLocale(locale);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
//            config.setLocale(locale);
//        resources.updateConfiguration(config,resources.getDisplayMetrics());
//        return context;
//    }
//
//    private static void Persist(Context context, String lang) {
//        CacheDiskUtils.getInstance().put(Constants.LANG,lang);
//    }
//
//    private static String getPersistedData(Context context,String lang)
//    {
//        return CacheDiskUtils.getInstance().getString(Constants.LANG);
//    }
}
