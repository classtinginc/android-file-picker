package com.classtinginc.file_picker.utils;

import android.content.Context;

import java.util.HashMap;

import com.classtinginc.file_picker.consts.TranslationKey;
import com.classtinginc.library.file_picker.R;

/**
 * Created by classting on 04/07/2019.
 */

public class TranslationUtils {

    public static HashMap<TranslationKey, String> translations = new HashMap<>();

    public static void setTranslations(HashMap<TranslationKey, String> translations) {
        if (translations != null) {
            TranslationUtils.translations = translations;
        } else {
            TranslationUtils.translations = new HashMap<>();
        }
    }

    public static String getButtonSelect(Context context) {
        if (translations.containsKey(TranslationKey.BTN_SELECT)) {
            return translations.get(TranslationKey.BTN_SELECT);
        }
        return context.getString(R.string.btn_select);
    }

    public static String getButtonCancel(Context context) {
        if (translations.containsKey(TranslationKey.BTN_CANCEL)) {
            return translations.get(TranslationKey.BTN_CANCEL);
        }
        return context.getString(R.string.btn_cancel);
    }

    public static String getToastGuideMaxFilesCount(Context context, int maxFilesCount) {
        if (translations.containsKey(TranslationKey.TOAST_GUIDE_MAX_FILES_COUNT)) {
            return String.format(translations.get(TranslationKey.TOAST_GUIDE_MAX_FILES_COUNT), maxFilesCount);
        }
        return context.getString(R.string.toast_guide_max_files_count, maxFilesCount);
    }

    public static String getToastGuideMaxFileSize(Context context, int maxFileSize) {
        if (translations.containsKey(TranslationKey.TOAST_GUIDE_MAX_FILE_SIZE)) {
            return String.format(translations.get(TranslationKey.TOAST_GUIDE_MAX_FILE_SIZE), maxFileSize);
        }
        return context.getString(R.string.toast_guide_max_file_size, maxFileSize);
    }

    public static String getToastRemovedStorage(Context context) {
        if (translations.containsKey(TranslationKey.TOAST_GUIDE_REMOVED_STORAGE)) {
            return translations.get(TranslationKey.TOAST_GUIDE_REMOVED_STORAGE);
        }
        return context.getString(R.string.toast_guide_storage_removed);
    }

    public static String getToastGuideErrorOnFile(Context context) {
        if (translations.containsKey(TranslationKey.TOAST_GUIDE_ERROR_ON_FILE)) {
            return translations.get(TranslationKey.TOAST_GUIDE_ERROR_ON_FILE);
        }
        return context.getString(R.string.toast_guide_error_on_file);
    }

    public static String getToastGuideIncorrectFileFormat(Context context) {
        if (translations.containsKey(TranslationKey.TOAST_GUIDE_INCORRECT_FILE_FORMAT)) {
            return translations.get(TranslationKey.TOAST_GUIDE_INCORRECT_FILE_FORMAT);
        }
        return context.getString(R.string.toast_guide_incorrect_file_format);
    }

    public static String getMsgSelectItem(Context context, int selectedItemsCount) {
        if (translations.containsKey(TranslationKey.MSG_SELECT_ITEM)) {
            return String.format(translations.get(TranslationKey.MSG_SELECT_ITEM), selectedItemsCount);
        }
        return context.getString(R.string.msg_select_item, selectedItemsCount);
    }

    public static String getMsgSelectItemPl(Context context, int selectedItemsCount) {
        if (translations.containsKey(TranslationKey.MSG_SELECT_ITEM_PL)) {
            return String.format(translations.get(TranslationKey.MSG_SELECT_ITEM_PL), selectedItemsCount);
        }
        return context.getString(R.string.msg_select_item_pl, selectedItemsCount);
    }

    public static String getMsgEmptyDir(Context context) {
        if (translations.containsKey(TranslationKey.MSG_EMPTY_DIR)) {
            return translations.get(TranslationKey.MSG_EMPTY_DIR);
        }
        return context.getString(R.string.msg_empty_directory);
    }
}
