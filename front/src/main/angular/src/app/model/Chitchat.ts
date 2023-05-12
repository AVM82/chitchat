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

  constructor(id: number, chatName: string, authorName: string,
              categoryName: string, description: string,avatarUrl: string, languageName: string,
              level: string, capacity: number, date: Date, usersInChitchat: string[]) {
    this.id = id;
    this.avatarUrl = avatarUrl
    this.chatName = chatName;
    this.authorName = authorName;
    this.categoryName = categoryName;
    this.description = description;
    this.languageName = languageName;
    this.level = level;
    this.capacity = capacity;
    this.date = date;
    this.usersInChitchat = usersInChitchat;
  }
}