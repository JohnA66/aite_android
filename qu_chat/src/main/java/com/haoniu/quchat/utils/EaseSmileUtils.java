/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haoniu.quchat.utils;

import android.content.Context;
import android.net.Uri;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.style.ImageSpan;

import com.haoniu.quchat.base.EaseUI;
import com.haoniu.quchat.base.EaseUI.EaseEmojiconInfoProvider;
import com.haoniu.quchat.domain.EaseEmojicon;
import com.haoniu.quchat.model.EaseDefaultEmojiconDatas;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EaseSmileUtils {
    public static final String DELETE_KEY = "em_delete_delete_expression";

    public static final String ee_1 = "[):]";
    public static final String ee_2 = "[:D]";
    public static final String ee_3 = "[;)]";
    public static final String ee_4 = "[:-o]";
    public static final String ee_5 = "[:p]";
    public static final String ee_6 = "[(H)]";
    public static final String ee_7 = "[:@]";
    public static final String ee_8 = "[:s]";
    public static final String ee_9 = "[:$]";
    public static final String ee_10 = "[:(]";
    public static final String ee_11 = "[:'(]";
    public static final String ee_12 = "[:|]";
    public static final String ee_13 = "[(a)]";
    public static final String ee_14 = "[8o|]";
    public static final String ee_15 = "[8-|]";
    public static final String ee_16 = "[+o(]";
    public static final String ee_17 = "[<o)]";
    public static final String ee_18 = "[|-)]";
    public static final String ee_19 = "[*-)]";
    public static final String ee_20 = "[:-#]";
    public static final String ee_21 = "[:-*]";
    public static final String ee_22 = "[^o)]";
    public static final String ee_23 = "[8-)]";
    public static final String ee_24 = "[(|)]";
    public static final String ee_25 = "[(u)]";
    public static final String ee_26 = "[(S)]";
    public static final String ee_27 = "[(*)]";
    public static final String ee_28 = "[(#)]";
    public static final String ee_29 = "[(R)]";
    public static final String ee_30 = "[({)]";
    public static final String ee_31 = "[(})]";
    public static final String ee_32 = "[(k)]";
    public static final String ee_33 = "[(F)]";
    public static final String ee_34 = "[(W)]";
    public static final String ee_35 = "[(D)]";
    public static final String ee_36 = "[:):]";
    public static final String ee_37 = "[::D]";
    public static final String ee_38 = "[:;)]";
    public static final String ee_39 = "[::-o]";
    public static final String ee_40 = "[::p]";
    public static final String ee_41 = "[)::(]";
    public static final String ee_42 = "[:):D:]";
    public static final String ee_43 = "[:);):]";
    public static final String ee_44 = "[:):-o:]";
    public static final String ee_45 = "[:):p:]";
    public static final String ee_46 = "[:)(H):]";
    public static final String ee_47 = "[:):@:]";
    public static final String ee_48 = "[:):s:]";
    public static final String ee_49 = "[:):$:]";
    public static final String ee_50 = "[::)):$:]";
    public static final String ee_51 = "[)::]";
    public static final String ee_52 = "[:D:]";
    public static final String ee_53 = "[;):]";
    public static final String ee_54 = "[:-o:]";
    public static final String ee_55 = "[:p:]";
    public static final String ee_56 = "[(H):]";
    public static final String ee_57 = "[:@:]";
    public static final String ee_58 = "[:s:]";
    public static final String ee_59 = "[:$:]";
    public static final String ee_60 = "[:(::(:]";
    public static final String ee_61 = "[:(::'(:]";
    public static final String ee_62 = "[:(::|:]";
    public static final String ee_63 = "[:(:(a):]";
    public static final String ee_64 = "[:(:8o|:]";
    public static final String ee_65 = "[:(:8-|:]";
    public static final String ee_66 = "[:(:+o(:]";
    public static final String ee_67 = "[:(:<o):]";
    public static final String ee_68 = "[:(:|-):]";
    public static final String ee_69 = "[:(:*-):]";
    public static final String ee_70 = "[:(:]";
    public static final String ee_71 = "[:'(:]";
    public static final String ee_72 = "[:|:]";
    public static final String ee_73 = "[(a):]";
    public static final String ee_74 = "[8o|:]";
    public static final String ee_75 = "[8-|:]";
    public static final String ee_76 = "[+o(:]";
    public static final String ee_77 = "[<o):]";
    public static final String ee_78 = "[|-):]";
    public static final String ee_79 = "[*-):]";
    public static final String ee_80 = "[:-#:]";
    public static final String ee_81 = "[:-*::]";
    public static final String ee_82 = "[^o)::]";
    public static final String ee_83 = "[8-)::]";
    public static final String ee_84 = "[(|)::]";
    public static final String ee_85 = "[(u)::]";
    public static final String ee_86 = "[(S)::]";
    public static final String ee_87 = "[(*)::]";
    public static final String ee_88 = "[(#)::]";
    public static final String ee_89 = "[(R)::]";
    public static final String ee_90 = "[({)::]";
    public static final String ee_91 = "[(})::]";
    public static final String ee_92 = "[(k)::]";
    public static final String ee_93 = "[(F)::]";
    public static final String ee_94 = "[(W)::]";
    public static final String ee_95 = "[(D)::]";
    public static final String ee_96 = "[:):::]";
    public static final String ee_97 = "[::D::]";
    public static final String ee_98 = "[:;)::]";
    public static final String ee_99 = "[::-o::]";
    public static final String ee_100 = "[::p::]";
    public static final String ee_101 = "[):::]";
    public static final String ee_102 = "[:D::]";
    public static final String ee_103 = "[;)::]";
    public static final String ee_104 = "[:-o::]";
    public static final String ee_105 = "[:p::]";
    public static final String ee_106 = "[(H)::]";
    public static final String ee_107 = "[:@::]";
    public static final String ee_108 = "[:s::]";
    public static final String ee_109 = "[:$::]";
    public static final String ee_110 = "[:(::]";
    public static final String ee_111 = "[:'(::]";
    public static final String ee_112 = "[:|::]";
    public static final String ee_113 = "[(a)::]";
    public static final String ee_114 = "[8o|::]";
    public static final String ee_115 = "[8-|::]";
    public static final String ee_116 = "[+o(::]";
    public static final String ee_117 = "[<o)::]";
    public static final String ee_118 = "[|-)::]";
    public static final String ee_119 = "[*-)::]";
    public static final String ee_120 = "[:-#::]";
    public static final String ee_121 = "[)::):]";
    public static final String ee_122 = "[:D:):]";
    public static final String ee_123 = "[;):):]";
    public static final String ee_124 = "[:-o:):]";
    public static final String ee_125 = "[:p:):]";
    public static final String ee_126 = "[(H):):]";
    public static final String ee_127 = "[:@:):]";
    public static final String ee_128 = "[:s:):]";
    public static final String ee_129 = "[:$:):]";
    public static final String ee_130 = "[:(:):]";
    public static final String ee_131 = "[:'(:):]";
    public static final String ee_132 = "[:|:):]";
    public static final String ee_133 = "[(a):):]";
    public static final String ee_134 = "[8o|:):]";
    public static final String ee_135 = "[8-|:):]";
    public static final String ee_136 = "[+o(:):]";
    public static final String ee_137 = "[<o):):]";
    public static final String ee_138 = "[|-):):]";
    public static final String ee_139 = "[*-):):]";
    public static final String ee_140 = "[:-#:):]";
    public static final String ee_141 = "[:-#:)::]";

    private static final Factory spannableFactory = Factory
            .getInstance();

    private static final Map<Pattern, Object> emoticons = new HashMap<Pattern, Object>();


    static {
        EaseEmojicon[] emojicons = EaseDefaultEmojiconDatas.getData();
        for (EaseEmojicon emojicon : emojicons) {
            addPattern(emojicon.getEmojiText(), emojicon.getIcon());
        }
        EaseEmojiconInfoProvider emojiconInfoProvider = EaseUI.getInstance().getEmojiconInfoProvider();
        if (emojiconInfoProvider != null && emojiconInfoProvider.getTextEmojiconMapping() != null) {
            for (Entry<String, Object> entry : emojiconInfoProvider.getTextEmojiconMapping().entrySet()) {
                addPattern(entry.getKey(), entry.getValue());
            }
        }

    }

    /**
     * add text and icon to the map
     * @param emojiText-- text of emoji
     * @param icon -- resource id or local path
     */
    public static void addPattern(String emojiText, Object icon) {
        emoticons.put(Pattern.compile(Pattern.quote(emojiText)), icon);
    }


    /**
     * replace existing spannable with smiles
     * @param context
     * @param spannable
     * @return
     */
    public static boolean addSmiles(Context context, Spannable spannable) {
        boolean hasChanges = false;
        for (Entry<Pattern, Object> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(),
                        matcher.end(), ImageSpan.class)) {
                    if (spannable.getSpanStart(span) >= matcher.start()
                            && spannable.getSpanEnd(span) <= matcher.end()) {
                        spannable.removeSpan(span);
                    } else {
                        set = false;
                        break;
                    }
                }
                if (set) {
                    hasChanges = true;
                    Object value = entry.getValue();
                    if (value instanceof String && !((String) value).startsWith("http")) {
                        File file = new File((String) value);
                        if (!file.exists() || file.isDirectory()) {
                            return false;
                        }
                        spannable.setSpan(new ImageSpan(context, Uri.fromFile(file)),
                                matcher.start(), matcher.end(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else {
                        spannable.setSpan(new ImageSpan(context, (Integer) value),
                                matcher.start(), matcher.end(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        }

        return hasChanges;
    }

    public static Spannable getSmiledText(Context context, CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addSmiles(context, spannable);
        return spannable;
    }

    public static boolean containsKey(String key) {
        boolean b = false;
        for (Entry<Pattern, Object> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(key);
            if (matcher.find()) {
                b = true;
                break;
            }
        }

        return b;
    }

    public static int getSmilesSize() {
        return emoticons.size();
    }


}
