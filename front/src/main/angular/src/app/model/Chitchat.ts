export class Chitchat {
  id: number;
  chatName: string;
  authorName: string;
  categoryName: string;
  avatarUrl: string ;
  description: string;
  languageName: string;
  level:string;
  capacity: number;
  date: Date;
  usersInChitchat:string[];
  countUnreadMessages:number


  constructor(id: number, chatName: string, authorName: string, categoryName: string, avatarUrl: string, description: string, languageName: string, level: string, capacity: number, date: Date, usersInChitchat: string[], countUnreadMessages: number) {
    this.id = id;
    this.chatName = chatName;
    this.authorName = authorName;
    this.categoryName = categoryName;
    this.avatarUrl = avatarUrl;
    this.description = description;
    this.languageName = languageName;
    this.level = level;
    this.capacity = capacity;
    this.date = date;
    this.usersInChitchat = usersInChitchat;
    this.countUnreadMessages = countUnreadMessages;
  }
}