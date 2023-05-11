export class NewChitChatDTO{
  chatHeader: string;
  categoryId: number;
  description: string;
  languageId : string;
  level: string;
  capacity: number;
  date: string;


  constructor(chatHeader: string, categoryId: number,
              description: string, languageId: string,
              level: string, capacity: number, date: string) {
    this.chatHeader = chatHeader;
    this.categoryId = categoryId;
    this.description = description;
    this.languageId = languageId;
    this.level = level;
    this.capacity = capacity;
    this.date = date;
  }
}