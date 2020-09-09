package com.ndsl.nw.bun133.api.mildom.jsons;

import java.net.URL;

public class Gifts {
    public Integer code;
    public GiftsBody body;

    public class GiftsBody{
        /**
         * W-hat?
         */
        public Integer[] danmu;
        public Gift[] models;
    }

    public class Gift{
        public Integer gift_id;
        public Integer category;
        public String name;
        public String[] Lang;
        public String Preview;
        public Integer status;
        public Integer sort;
        public Integer type;
        public URL pic;
        public Integer price;
        public String intro;
        public Integer gift_play_type;
        public Integer[] challenge_selectable_nums;
        public String face_effect_text;
        public Integer face_gift;
        public Combo[] combo_list;
    }

    public class Combo{
        public Integer combo_effect;
        public Integer quantity;
    }
}
