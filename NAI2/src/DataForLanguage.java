public class DataForLanguage {


    // /_ jest po to zeby przechowywac jedna instancje przykladu danych treningowych lub testowych

   private String language;
   private String text;


   public DataForLanguage(String language, String text) {
       this.language = language;
       this.text = text;
   }

   public String getLanguage() {
       return language;
   }

   public String getText() {
       return text;
   }

}
