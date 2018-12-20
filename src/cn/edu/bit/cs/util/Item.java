package cn.edu.bit.cs.util;
 
 
public enum Item{
		 salary, finance,other, transfer,   
		 food, clothes, gift,   entertainment, digital, transport,  
		  book,  daily_use,  game,  hotel, heathcare;		  
	
	 public static Item toItem(String str) {
		  for(Item i:Item.values())
		  {
			  if(i.toString().equals(str))
				  return i;
		  }
		  return salary;//
	 } 
	 	public static String change(Item item) {
		 	switch (item) { 
		 			case salary: return "工资";
		 			case finance: return "投资收益"; 
		 			case other:  return "其他";
		 			case transfer: return "转账";
		 			
		 			case food: return "餐饮";
		 			case clothes: return "衣服";
		 			case gift: return "人情往来";
		 			case entertainment: return "娱乐";
		 			case digital: return "数码";
		 			case transport: return "交通";
		 			
		 			case book: return "书籍教育";
		 			case daily_use: return "生活用品";
		 			case game: return "游戏";
		 			case hotel: return "住宿";
		 			case heathcare: return "健康"; 
		 			  
		 			default: return "项目";
		 		} 
	 	} 
 } 
