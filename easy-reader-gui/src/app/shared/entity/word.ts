export class Word {
  private id: number;
  private wordName: string;
  private transcription: string;
  private translation: string;
  private status: string;
  private bookName: string;
  private context: string;
}

export enum Status{
  IN_PROGRESS,
  LEARNED,
  NEW
}
