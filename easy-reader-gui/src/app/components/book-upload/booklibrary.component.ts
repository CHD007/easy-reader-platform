import {Component} from '@angular/core';
import {Book} from "../../shared/book";

@Component({
  selector: 'book-library',
  templateUrl: 'booklibrary.component.html',
  styleUrls: ['booklibrary.component.scss']
})
export class BookLibraryComponent {

  private books:Array<Book> = [];

  constructor(){
    this.books.push(new Book("Alice in Wonderland",2453,876,1200));
    this.books.push(new Book("The Story of Eugene Titkov",2453,876,1200));
    this.books.push(new Book("Nothing but truth",2453,876,1200));
  }

}


