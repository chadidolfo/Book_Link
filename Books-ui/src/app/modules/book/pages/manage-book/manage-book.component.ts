import { Component } from '@angular/core';
import {BookRequest} from '../../../../services/models/book-request';
import {BookService} from '../../../../services/services/book.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-manage-book',
  templateUrl: './manage-book.component.html',
  styleUrl: './manage-book.component.scss'
})
export class ManageBookComponent {

  errorMsg: Array<string> = [];
  bookRequest: BookRequest = {
    authorName: '',
    isbn: '',
    synopsis: '',
    title: ''
  };
  selectedPicture: string | undefined;
  selectedBookCover: any;


  onFileSelected(event: any) {
    this.selectedBookCover = event.target.files[0];
    console.log(this.selectedBookCover);

    if (this.selectedBookCover) {

      const reader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;
      };
      reader.readAsDataURL(this.selectedBookCover);
    }
  }

  saveBook() {
    // this.bookService.saveBook({
    //   body: this.bookRequest
    // }).subscribe({
    //   next: (bookId) => {
    //     this.bookService.uploadBookCoverPicture({
    //       'book-id': bookId,
    //       body: {
    //         file: this.selectedBookCover
    //       }
    //     }).subscribe({
    //       next: () => {
    //         this.router.navigate(['/books/my-books']);
    //       }
    //     });
    //   },
    //   error: (err) => {
    //     console.log(err.error);
    //     this.errorMsg = err.error.validationErrors;
    //   }
   // });
  }
}
