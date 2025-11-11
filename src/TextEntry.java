package JournalApp;

//textEntry.java
public class TextEntry extends JournalEntry {
      private static final long serialVersionUID = 1L; // シリアライズIDを追加

      private String text;

      public TextEntry(String text) {
            super();//親クラスのコンストラクタを呼び出す
            this.text = text;
      }

      @Override
      public String getDisplayString() {
            return "Text Entry: " + text + " (Timestamp: " + getTimestamp() + ")";
      }
}