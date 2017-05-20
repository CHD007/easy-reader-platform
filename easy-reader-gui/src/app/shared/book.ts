export class Book {

  constructor(public name: string,
              public words: number,
              public learned: number,
              public inProgress: number,
              public newWords?:number) {
    this.name = name;
    this.words = words;
    this.learned = learned;
    this.inProgress = inProgress;
    this.newWords = words - learned - inProgress;

  }
}
