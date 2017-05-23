export class Routes {
  public static baseUrl: string = 'http://localhost:8080/easy-reader-api';
  public static UPLOAD_FILE: string = `${Routes.baseUrl}/upload`;
  public static GET_ALL_BOOKS: string = `${Routes.baseUrl}/books`;
  public static DELETE_BOOK_BY_ID: string = `${Routes.baseUrl}/books/delete`;

  public static getAllWordsByBookId(id: string) {
    return `${Routes.baseUrl}/books/${id}/book_words`;
  }
}
