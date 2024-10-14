package tobyspring.hellospring;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SortTest {
	Sort sort;

	@BeforeEach
	void prepare(){
		sort = new Sort();
	}

	@Test
	void sort() {
		List<String> list = sort.sortByLength(Arrays.asList("aa", "b"));
		Assertions.assertThat(list).isEqualTo(List.of("b", "aa"));
	}

	@Test
	void sort3Items() {
		List<String> list = sort.sortByLength(Arrays.asList("aa", "ccc", "b"));
		Assertions.assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
	}

	@Test
	void sortAlreadySorted() {
		List<String> list = sort.sortByLength(Arrays.asList("b", "aa", "ccc"));
		Assertions.assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
	}
}
