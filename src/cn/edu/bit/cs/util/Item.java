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
		 			case salary: return "����";
		 			case finance: return "Ͷ������"; 
		 			case other:  return "����";
		 			case transfer: return "ת��";
		 			
		 			case food: return "����";
		 			case clothes: return "�·�";
		 			case gift: return "��������";
		 			case entertainment: return "����";
		 			case digital: return "����";
		 			case transport: return "��ͨ";
		 			
		 			case book: return "�鼮����";
		 			case daily_use: return "������Ʒ";
		 			case game: return "��Ϸ";
		 			case hotel: return "ס��";
		 			case heathcare: return "����"; 
		 			  
		 			default: return "��Ŀ";
		 		} 
	 	} 
 } 
