package com.aakkus.dc.service;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class PreventDoubleClickServiceTest {

    @InjectMocks
    PreventDoubleClickService preventDoubleClickService;

    @Test
    public void should_expired_key_when_key_is_not_exist() {
        //Given
        String key = "123ert678ıopTestController.getrq(..)";

        //When
        boolean expired = preventDoubleClickService.isExpired(key);

        //Then
        assertThat(expired).isTrue();
    }

    @Test
    public void should_expired_key_when_key_exist_but_time_is_expired() {
        //Given
        String key = "123ert678ıopTestController.getrq(..)";
        preventDoubleClickService.put(key, Pair.of(System.currentTimeMillis() - 101L, 100L));

        //When
        boolean expired = preventDoubleClickService.isExpired(key);

        //Then
        assertThat(expired).isTrue();
    }

    @Test
    public void should_not_expired_key_when_key_exist_and_still_available() {
        //Given
        String key = "123ert678ıopTestController.getrq(..)";
        preventDoubleClickService.put(key, Pair.of(System.currentTimeMillis(), 100L));

        //When
        boolean expired = preventDoubleClickService.isExpired(key);

        //Then
        assertThat(expired).isFalse();
    }
}