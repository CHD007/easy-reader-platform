export class Word {
  public id: number;
  public wordName: string;
  public transcription: string;
  public translation: string;
  public status: string;
  public bookName: string;
  public context: string;
}

export enum Status{
  IN_PROGRESS,
  LEARNED,
  NEW
}
