package tobyspring.hellospring.learningtest;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClockTest {
	// Clock 이용해서 LocalDateTime.now?
	@Test
	void  clock(){
		Clock clock = Clock.systemDefaultZone();
		LocalDateTime dt1 = LocalDateTime.now(clock);
		LocalDateTime dt2 = LocalDateTime.now(clock);
		Assertions.assertThat(dt2).isEqualTo(dt1);
	}

	@Test
	// Clock을 Test에서 사용할 때 내가 원하는 시간을 지정해서 현재 시간을 가져오게 할 수 있는가?
	void fixedClock(){
		Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
		LocalDateTime dt1 = LocalDateTime.now(clock);
		LocalDateTime dt2 = LocalDateTime.now(clock).plusHours(1);

		Assertions.assertThat(dt2).isEqualTo(dt1.plusHours(1));
	}
}
