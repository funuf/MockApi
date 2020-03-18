package fun.hellofun.source;

import com.alibaba.fastjson.JSON;
import fun.hellofun.command.topic.ImageTopic;
import fun.hellofun.jUtils.predicate.empty.Empty;
import fun.hellofun.command.topic.Topic;
import okio.Okio;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月17日 星期二 8时32分53秒 创建；<br>
 * 作用是：<b>图片源</b>；<br>
 */
class SourceImage extends Source<String> {

    @Override
    protected String get(Topic topic) {
        return list(topic, 1).get(0);
    }

    @Override
    protected List<String> list(Topic topic, Integer limit) {
        return take(limit, pool(((ImageTopic) topic)));
    }

    private List<String> ANIMALS = null;
    private List<String> BOYS = null;
    private List<String> CARS = null;
    private List<String> FOODS = null;
    private List<String> GIRLS = null;
    private List<String> LANDSCAPES = null;
    private List<String> PLANTS = null;
    private List<String> BANNERS = null;

    /**
     * 从数据池中取值
     */
    private List<String> take(Integer limit, List<String> fromPool) {
        List<String> result = new ArrayList<>();
        if (limit == null) {
            limit = DEFAULT_LIMIT;
        }
        for (Integer i = 0; i < limit; i++) {
            result.add(fromPool.get(new Random().nextInt(fromPool.size())));
        }
        return result;
    }

    /**
     * 确定数据池
     */
    private List<String> pool(ImageTopic topic) {
        init();
        if (topic == null) {
            return all();
        }
        switch (topic) {
            case ANIMAL:
                return ANIMALS;
            case BOY:
                return BOYS;
            case CAR:
                return CARS;
            case BANNER:
                return BANNERS;
            case FOOD:
                return FOODS;
            case GIRL:
                return GIRLS;
            case PLANT:
                return PLANTS;
            case LANDSCAPE:
                return LANDSCAPES;
            default:
                return all();
        }
    }

    private List<String> all() {
        return new ArrayList<String>() {{
            addAll(ANIMALS);
            addAll(BOYS);
            addAll(CARS);
            addAll(FOODS);
            addAll(GIRLS);
            addAll(LANDSCAPES);
            addAll(PLANTS);
            addAll(BANNERS);
        }};
    }

    private void init() {
        if (Empty.yes(ANIMALS, BOYS, CARS, FOODS, GIRLS, LANDSCAPES, PLANTS, BANNERS)) {
            try {
                ANIMALS = JSON.parseArray(Okio.buffer(Okio.source(new ClassPathResource("image/animal.json").getInputStream())).readUtf8(), String.class);
                BOYS = JSON.parseArray(Okio.buffer(Okio.source(new ClassPathResource("image/boy.json").getInputStream())).readUtf8(), String.class);
                CARS = JSON.parseArray(Okio.buffer(Okio.source(new ClassPathResource("image/car.json").getInputStream())).readUtf8(), String.class);
                FOODS = JSON.parseArray(Okio.buffer(Okio.source(new ClassPathResource("image/food.json").getInputStream())).readUtf8(), String.class);
                GIRLS = JSON.parseArray(Okio.buffer(Okio.source(new ClassPathResource("image/girl.json").getInputStream())).readUtf8(), String.class);
                LANDSCAPES = JSON.parseArray(Okio.buffer(Okio.source(new ClassPathResource("image/landscape.json").getInputStream())).readUtf8(), String.class);
                PLANTS = JSON.parseArray(Okio.buffer(Okio.source(new ClassPathResource("image/plant.json").getInputStream())).readUtf8(), String.class);
                BANNERS = JSON.parseArray(Okio.buffer(Okio.source(new ClassPathResource("image/banner.json").getInputStream())).readUtf8(), String.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
