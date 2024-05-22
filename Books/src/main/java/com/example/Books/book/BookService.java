package com.example.Books.book;

import com.example.Books.common.PageResponse;
import com.example.Books.history.BookTransactionHistory;
import com.example.Books.history.BookTransactionHistoryRepository;
import com.example.Books.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static com.example.Books.book.BookSpecification.withOwnerId;

@Service
@RequiredArgsConstructor
//@Slf4j
//@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookTransactionHistoryRepository transactionHistoryRepository;
//    private final FileStorageService fileStorageService;
//
    public Integer  save(BookRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(request);
        book.setOwner(user);
        return bookRepository.save(book).getId();

    }


    public BookResponse findById(Integer bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
    }


    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());//sort list of books by
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
        List<BookResponse> booksResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );

    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {

            User user = ((User) connectedUser.getPrincipal());
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
            Page<Book> books = bookRepository.findAll(withOwnerId(user.getId()), pageable); //(BookRepository) JpaSpecificationExecutor
            List<BookResponse> booksResponse = books.stream()
                    .map(bookMapper::toBookResponse)
                    .toList();
            return new PageResponse<>(
                    booksResponse,
                    books.getNumber(),
                    books.getSize(),
                    books.getTotalElements(),
                    books.getTotalPages(),
                    books.isFirst(),
                    books.isLast()
            );

    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {


            User user = ((User) connectedUser.getPrincipal());
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
            Page<BookTransactionHistory> allBorrowedBooks = transactionHistoryRepository.findAllBorrowedBooks(pageable, user.getId());
            List<BorrowedBookResponse> booksResponse = allBorrowedBooks.stream()
                    .map(bookMapper::toBorrowedBookResponse)
                    .toList();
            return new PageResponse<>(
                    booksResponse,
                    allBorrowedBooks.getNumber(),
                    allBorrowedBooks.getSize(),
                    allBorrowedBooks.getTotalElements(),
                    allBorrowedBooks.getTotalPages(),
                    allBorrowedBooks.isFirst(),
                    allBorrowedBooks.isLast()
            );

    }
}
