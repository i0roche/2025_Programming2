package JournalApp;

//JournalEntry.java

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class JournalEntry implements Serializable {

      private static final long serialVersionUID = 1L;

      protected transient LocalDateTime timestamp;

      public JournalEntry() {
            this.timestamp = LocalDateTime.now();
      }

      public String getTimestamp() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return timestamp.format(formatter);
      }
      public abstract String getDisplayString();

      // カスタムの直列化メソッド (transientフィールドの保存に
      private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
            // LocalDateTimeを文字列に変換して保存
            out.writeObject(timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
      }

      // カスタムの非直列化メソッド (transientフィールドの復元に
      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            // 保存した文字列からLocalDateTimeを復元
            String timestampString = (String) in.readObject();
            this.timestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
      }
}