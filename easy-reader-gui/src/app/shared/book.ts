/**
 * Book DTO
 * @author Evgenii Ray
 */
export class Book {

  constructor(public bookName: string,
              public newWords: number,
              public id: number,
              public words: number,
              public learnedWords: number,
              public inProgress: number) {
    this.newWords = newWords;
    this.id = id;
    this.bookName = bookName;
    this.words = words;
    this.learnedWords = learnedWords;
    this.inProgress = inProgress;
  }

}
