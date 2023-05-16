export class Message {
  private createdAt: string;
  private userName: string;
  private chitchatId: number;
  private message: string;


  constructor(createdAt: string, userName: string, chitchatId: number, message: string) {
    this.createdAt = createdAt;
    this.userName = userName;
    this.chitchatId = chitchatId;
    this.message = message;
  }
}