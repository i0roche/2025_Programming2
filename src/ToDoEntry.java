package JournalApp;

//ToDoEntry.java
import java.util.ArrayList;
import java.util.List;

public class ToDoEntry extends JournalEntry {
      private static final long serialVersionUID = 1L; // シリアライズIDを追加

      private String task;
      private boolean isCompleted;
      private List<String> tags;

      public ToDoEntry(String task) {
            super(); //親クラスのコンストラクタを呼び出す
            this.task = task;
            this.isCompleted = false;
            this.tags = new ArrayList<>();
      }

      public void markAsCompleted() {
            this.isCompleted = true;
      }

      public void addTag(String tag) {
            tags.add(tag);
      }

      @Override
      public String getDisplayString() {
            String status = isCompleted ? "Completed" : "Pending";
            String tagString = String.join(", ", tags);
            if (!tagString.isEmpty()) {
                  tagString = " [Tags: " + tagString + "]";
            }
            return "To-Do Entry: " + task + " (Status: " + status + ", Timestamp: " + getTimestamp() + ")" + tagString;
      }
}