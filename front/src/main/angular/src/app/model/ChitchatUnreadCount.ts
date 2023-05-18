export class ChitchatUnreadCount {
  chitchatId: number;
  unreadCount: number;


  constructor(chitchatId: number, unreadCount: number) {
    this.chitchatId = chitchatId;
    this.unreadCount = unreadCount;
  }
}