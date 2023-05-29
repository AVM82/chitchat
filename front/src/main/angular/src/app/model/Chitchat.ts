export class Chitchat {
  id: number;
  chatName: string;
  authorLanguage: string;
  authorName: string;
  categoryName: string;
  avatarUrl: string;
  description: string;
  languageName: string;
  level: string;
  capacity: number;
  date: Date;
  usersInChitchat: string[];
  conferenceLink: string;
  countUnreadMessages: number;


  constructor(id: number, chatName: string,authorLanguage: string, authorName: string, categoryName: string,
              avatarUrl: string, description: string, languageName: string, level: string,
              capacity: number, date: Date, usersInChitchat: string[], conferenceLink: string,
              countUnreadMessages: number) {
    this.id = id;
    this.chatName = chatName;
    this.authorLanguage = authorLanguage;
    this.authorName = authorName;
    this.categoryName = categoryName;
    this.avatarUrl = avatarUrl;
    this.description = description;
    this.languageName = languageName;
    this.level = level;
    this.capacity = capacity;
    this.date = date;
    this.usersInChitchat = usersInChitchat;
    this.conferenceLink = conferenceLink;
    this.countUnreadMessages = countUnreadMessages;
  }
}