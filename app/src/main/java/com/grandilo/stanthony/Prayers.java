/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grandilo.stanthony;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Prayers {

    private static final Random RANDOM = new Random();

    public static int getRandomCheeseDrawable() {
        switch (RANDOM.nextInt(8)) {
            default:
            case 0:
                return R.drawable.img1;
            case 1:
                return R.drawable.img2;
            case 2:
                return R.drawable.img3;
            case 3:
                return R.drawable.img4;
            case 4:
                return R.drawable.img5;
            case 5:
                return R.drawable.img6;
            case 6:
                return R.drawable.img7;

        }
    }

        public static final List<String> specialPrayerList = Arrays.asList("PRAYER SEQUENCE FOR A DECEASED MEMBER ",
                "Act of Confidence",
                "Prayer to St. Anthony for Peace of Mind.",
                "Prayer for Employment ",
                "Prayer to St. Anthony in Affliction",
                "St. Anthony’s Prayer to Infant Jesus",
                "Prayer for Those Who Request Our Prayers",
                "Prayer for Deceased Members",
                "Act of Consecration to the Sacred Heart of Jesus",
                "Prayer at the Blessing of Lilies ",
                "The Prayer of a Christian Youth",
                "Prayer to St. Anthony for the Recovery of Lost Graces",
                "Prayer for the Restoration of Things Lost or Stolen",
                "Prayers of Intercession",
                "Anthonian’s Prayer of Charitability",
                "Prayer in Honor of St Anthony’s Tongue ",
                "Children’s Prayer for Parents ",
                "Prayer for One’s Vocation",
                "Prayer for Engaged Couples",
                "Prayer for Married Couple",
                "Prayer for A Sick Person",
                "Prayer of Teenagers ",
                " Prayer for the Gift of Offspring ");
/*
* I am going to use this method in the subsequent version
* */
    public String[] prayerQoutes = {
            "Rejoice always, pray continually, give thanks in all circumstances; for this is God’s will for you in Christ Jesus.\n-1 Thessalonians 5:16-18",
            "Devote yourselves to prayer, being watchful and thankful.\n-Colossians 4:2 ",
            "“For where two or three gather in my name, there am I with them.”\n-Matthew 18:20 ",
            "Call to me and I will answer you and tell you great and unsearchable things you do not know.\n-Jeremiah 33:3",
            "And if we know that he hears us—whatever we ask—we know that we have what we asked of him.\n-1 John 5:15",
            "They all joined together constantly in prayer, along with the women and Mary the mother of Jesus, and with his brothers.\n-Acts 1:14",
            "You did not choose me, but I chose you and appointed you so that you might go and bear fruit—fruit that will last—and so that whatever you ask in my name the Father will give you.\n" +
                    " - John 15:16 "
        };
}
