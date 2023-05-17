export class Message {
  createdAt: string;
  userName: string;
  chitchatId: number;
  message: string;


  constructor(createdAt: string, userName: string, chitchatId: number, message: string) {
    this.createdAt = createdAt;
    this.userName = userName;
    this.chitchatId = chitchatId;
    this.message = message;
  }
}