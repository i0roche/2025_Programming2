package JournalApp;

//JournalApp.java
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JournalApp {
      private static final String FILE_NAME = "journal_entries.ser"; // 保存ファイル名
      private List<JournalEntry> entries;
      private Scanner scanner;

      //コンストラクタ
      public JournalApp() {
            // アプリ起動時にエントリをファイルから読み込む
            entries = loadEntries();
            scanner = new Scanner(System.in);
      }

      // エントリをファイルから読み込むメソッド
      private List<JournalEntry> loadEntries() {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                  // ファイルからList<JournalEntry>全体を読み込む
                  System.out.println("Loading existing journal entries from " + FILE_NAME + "...");
                  @SuppressWarnings("unchecked")
                  List<JournalEntry> loadedEntries = (List<JournalEntry>) ois.readObject();
                  System.out.println(loadedEntries.size() + " entries loaded successfully.");
                  return loadedEntries;
            } catch (IOException | ClassNotFoundException e) {
                  //読み込みエラーの場合
                  System.out.println("No existing journal file found or failed to load. Starting with an empty list.");
                  return new ArrayList<>();
            }
      }

      // エントリをファイルに保存するメソッド
      public void saveEntries() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
                  oos.writeObject(entries);
                  System.out.println("Journal entries saved to " + FILE_NAME);
            } catch (IOException e) {
                  System.err.println("Failed to save journal entries: " + e.getMessage());
            }
      }
      
      public List<JournalEntry> getEntries() {
            return this.entries;
      }


      //テキストエントリを追加するメソッド
      public void addTextEntry(String text) {
            TextEntry textEntry = new TextEntry(text);
            entries.add(textEntry);
            System.out.println("Text entry added.");
      }

      //ToDoエントリを追加するメソッド
      public void addToDoEntry(String task) {
            ToDoEntry toDoEntry = new ToDoEntry(task);
            entries.add(toDoEntry);
            System.out.println("To-Do entry added.");
      }

      //ToDoエントリを完了済みにするメソッド
      public void markToDoAsCompleted(int index) {
            if (index >= 0 && index < entries.size() && entries.get(index) instanceof ToDoEntry) {
                  ToDoEntry toDoEntry = (ToDoEntry) entries.get(index);
                  toDoEntry.markAsCompleted();
                  System.out.println("To-Do entry marked as completed.");
            } else {
                  System.out.println("Invalid index or entry is not a To-Do.");
            }
      }

      //ToDoエントリにタグを追加するメソッド
      public void addTagToToDo(int index, String tag) {
            if (index >= 0 && index < entries.size() && entries.get(index) instanceof ToDoEntry) {
                  ToDoEntry toDoEntry = (ToDoEntry) entries.get(index);
                  toDoEntry.addTag(tag);
                  System.out.println("Tag added to To-Do entry.");
            } else {
                  System.out.println("Invalid index or entry is not a To-Do.");
            }
      }

      //すべてのエントリを表示するメソッド
      public void displayEntries() {
            if (entries.isEmpty()) {
                  System.out.println("No journal entries available.");
            } else {
                  for (int i = 0; i < entries.size(); i++) {
                        System.out.println(i + ": " + entries.get(i).getDisplayString());
                  }
            }
      }

      //アプリのメインループ
      public void run() {
            while (true) {
                  System.out.println("\nJournal App Menu:");
                  System.out.println("1. Add Text Entry");
                  System.out.println("2. Add To-Do Entry");
                  System.out.println("3. Mark To-Do as Completed");
                  System.out.println("4. Add Tag to To-Do");
                  System.out.println("5. Display All Entries");
                  System.out.println("6. Exit");
                  System.out.print("Choose an option: ");

                  int choice = scanner.nextInt();
                  scanner.nextLine(); // Consume newline
                  switch (choice) {
                        case 1:
                              System.out.print("Enter text: ");
                              String text = scanner.nextLine();
                              addTextEntry(text);
                              break;
                        case 2:
                              System.out.print("Enter task: ");
                              String task = scanner.nextLine();
                              addToDoEntry(task);
                              break;
                        case 3:
                              System.out.print("Enter index of To-Do to mark as completed: ");
                              int completeIndex = scanner.nextInt();
                              markToDoAsCompleted(completeIndex);
                              break;
                        case 4:
                              System.out.print("Enter index of To-Do to add tag: ");
                              int tagIndex = scanner.nextInt();
                              scanner.nextLine(); // Consume newline
                              System.out.print("Enter tag: ");
                              String tag = scanner.nextLine();
                              addTagToToDo(tagIndex, tag);
                              break;
                        case 5:
                              displayEntries();
                              break;
                        case 6:
                              saveEntries();
                              System.out.println("Exiting Journal App. Goodbye!");
                              return;
                        default:
                              System.out.println("Invalid choice. Please try again.");
                  }
            }
      }

      //アプリのエントリポイント
      public static void main(String[] args) {
            JournalApp app = new JournalApp();
            app.run();
      }
}