package ru.job4j.concurrent.cash;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

class CacheTest {

    @Test
    void whenAddRepeat() {
        Cache cache = new Cache();
        assertThat(cache.add(new Base(1, 1))).isTrue();
        assertThat(cache.add(new Base(1, 1))).isFalse();
    }

    @Test
    void whenUpdateVersionExeption() {       
        Cache cache = new Cache();
        cache.add(new Base(1, 1));        
        assertThatThrownBy(() -> cache.update(new Base(1, 2))).
        isInstanceOf(OptimisticException.class);
    }
    

    @Test
    void whenUpdateAndImmutableOk() {       
        Cache cache = new Cache();
        Base updatedBase = new Base(1, 1);
        updatedBase.setName("1");
        cache.add(updatedBase);
        updatedBase.setName("2");
        
        assertThat(cache.get(1).getName()).isEqualTo("1");
        cache.update(updatedBase);
        updatedBase.setName("3");
        
        assertThat(cache.get(1).getName()).isEqualTo("2");
        assertThat(cache.get(1).getVersion()).isEqualTo(2);
    }

    @Test
    void whenDeleteOk() {
        Cache cache = new Cache();
        Base deletedBase = new Base(1, 1);
        assertThat(cache.add(deletedBase)).isTrue();
        cache.delete(deletedBase);
        assertThat(cache.get(1)).isNull();
    }
}
