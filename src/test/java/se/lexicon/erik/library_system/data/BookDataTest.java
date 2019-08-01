package se.lexicon.erik.library_system.data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.lexicon.erik.library_system.model.Book;
import static org.junit.Assert.*;

public class BookDataTest {
	
	private BookDao testObject;
	private int bookId;
	
	public List<Book> data(){
		return Arrays.asList(
				new Book("Test 1", 10, BigDecimal.valueOf(10), "Test 1 description"),
				new Book("test 1", 10, BigDecimal.valueOf(10), "Test 1 description")				
				);
	}
	
	@Before
	public void init() {
		testObject = BookData.getInstance();
		testObject.removeAll();
		for(Book book : data()) {
			testObject.save(book);
		}
		Book book3 = new Book("Test 3", 10, BigDecimal.valueOf(10), "Test 3 description");
		book3.setAvailable(false);
		book3.setReserved(true);
		testObject.save(book3);
		bookId = book3.getBookId();		
	}
	
	@Test
	public void test_findById_bookId_return_not_null() {
		assertNotNull(testObject.findById(bookId).get());
	}
	
	@Test
	public void test_findById_invalid_return_empty_optional(){
		assertFalse(testObject.findById(-1).isPresent());
	}
	
	@Test
	public void test_findByTitle_test_1_return_list_size_2() {
		String param = "test 1";
		List<Book> result = testObject.findByTitle(param);
		
		int expectedSize = 2;
		assertEquals(expectedSize, result.size());		
	}
	
	@Test
	public void test_findByAvailable_true_return_list_of_size_2() {
		List<Book> result = testObject.findByAvailable(true);
		int expectedSize = 2;
		
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void test_findByReserved_true_return_list_of_size_1() {
		List<Book> result = testObject.findByReserved(true);
		int expectedSize = 1;
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void test_delete_by_bookId_book_get_deleted() {
		testObject.delete(bookId);
		
		assertFalse(testObject.findById(bookId).isPresent());
	}

}
