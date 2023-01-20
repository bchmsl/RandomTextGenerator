import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {
    public ChoiceBox<String> cbAlphabet;
    public Spinner<Integer> spNumberSentences;
    public Spinner<Integer> spMinWordsSentences;
    public Spinner<Integer> spMaxWordsSentences;
    public Spinner<Integer> spMinCharsWord;
    public Spinner<Integer> spMaxCharsWord;
    public Button btnGenerate;
    public Label lbOutput;
    public TextArea taResult;

    private final char[] alphabetGeoConsonants = {'ბ', 'გ', 'დ', 'ვ', 'ზ', 'თ', 'კ', 'ლ', 'მ', 'ნ', 'პ', 'ჟ', 'რ', 'ს', 'ტ', 'ფ', 'ქ', 'ღ', 'ყ', 'შ', 'ჩ', 'ც', 'ძ', 'წ', 'ჭ', 'ხ', 'ჯ', 'ჰ'};
    private final char[] alphabetGeoVowels = {'ა', 'ე', 'ი', 'ო', 'უ'};

    private final char[] alphabetEngConsonants = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};
    private final char[] alphabetEngVowels = {'a', 'e', 'i', 'o', 'u'};

    private int outputNumberOfSentences = 0;
    private int outputNumberOfWords = 0;
    private int outputNumberOfChars = 0;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Random Text Generator");
            stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("random_text_generator.fxml")))));
            stage.show();
        } catch (Exception e) {
            System.out.printf("Unable to start application due to exception: %s", e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spNumberSentences.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 5));
        spMinWordsSentences.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 5));
        spMaxWordsSentences.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 10));
        spMinCharsWord.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 5));
        spMaxCharsWord.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 10));
        cbAlphabet.getItems().addAll("Georgian", "English");
        cbAlphabet.setValue("English");
    }

    public void generateClicker() {
        int numberSentences = spNumberSentences.getValue();
        int minWordsSentences = spMinWordsSentences.getValue();
        int maxWordsSentences = spMaxWordsSentences.getValue();
        int minCharsWord = spMinCharsWord.getValue();
        int maxCharsWord = spMaxCharsWord.getValue();

        if (Objects.equals(cbAlphabet.getValue(), "Georgian")) {
            taResult.setText(generateRandomGeorgianText(numberSentences, minWordsSentences, maxWordsSentences, minCharsWord, maxCharsWord));
        } else {
            taResult.setText(generateRandomEnglishText(numberSentences, minWordsSentences, maxWordsSentences, minCharsWord, maxCharsWord));
        }

        lbOutput.setText(String.format("Sentences: %d, Words: %d, Characters: %d", outputNumberOfSentences, outputNumberOfWords, outputNumberOfChars));
        outputNumberOfSentences = 0;
        outputNumberOfWords = 0;
        outputNumberOfChars = 0;
    }

    private String generateRandomEnglishText(int numberOfSentences, int minWordsInSentences, int maxWordsInSentences, int minCharactersInWords, int maxCharactersInWords) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        boolean isVowel = random.nextBoolean();
        boolean isFirstWord = true;
        int numberOfWordsInSentences;
        int numberOfCharsInWords;
        for (int sentences = 0; sentences < numberOfSentences; sentences++) {
            numberOfWordsInSentences = random.nextInt(minWordsInSentences, maxWordsInSentences);
            for (int words = 0; words < numberOfWordsInSentences; words++) {
                numberOfCharsInWords = random.nextInt(minCharactersInWords, maxCharactersInWords);
                for (int chars = 0; chars < numberOfCharsInWords; chars++) {
                    isVowel = !isVowel;
                    char randomChar;
                    if (isVowel) {
                        randomChar = alphabetEngVowels[random.nextInt(0, alphabetEngVowels.length)];
                    } else {
                        randomChar = alphabetEngConsonants[random.nextInt(0, alphabetEngConsonants.length)];
                    }
                    if (isFirstWord) {
                        result.append(String.valueOf(randomChar).toUpperCase());
                        isFirstWord = false;
                    } else {
                        result.append(randomChar);
                    }
                    outputNumberOfChars += 1;
                }
                if (words != numberOfWordsInSentences - 1) {
                    result.append(' ');
                } else {
                    result.append(". ");
                    isFirstWord = true;
                }
                outputNumberOfWords += 1;
            }
            outputNumberOfSentences += 1;
        }
        return result.toString();
    }

    private String generateRandomGeorgianText(int numberOfSentences, int minWordsInSentences, int maxWordsInSentences, int minCharactersInWords, int maxCharactersInWords) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        boolean isVowel = random.nextBoolean();
        int numberOfWordsInSentences;
        int numberOfCharsInWords;
        char randomChar;
        for (int sentences = 0; sentences < numberOfSentences; sentences++) {
            numberOfWordsInSentences = random.nextInt(minWordsInSentences, maxWordsInSentences);
            for (int words = 0; words < numberOfWordsInSentences; words++) {
                numberOfCharsInWords = random.nextInt(minCharactersInWords, maxCharactersInWords);
                for (int chars = 0; chars < numberOfCharsInWords; chars++) {
                    isVowel = !isVowel;
                    if (isVowel) {
                        randomChar = alphabetGeoVowels[random.nextInt(0, alphabetGeoVowels.length)];
                    } else {
                        randomChar = alphabetGeoConsonants[random.nextInt(0, alphabetGeoConsonants.length)];
                    }
                    result.append(randomChar);
                    outputNumberOfChars += 1;
                }
                if (words != numberOfWordsInSentences - 1) {
                    result.append(' ');
                } else {
                    result.append(". ");
                }
                outputNumberOfWords += 1;
            }
            outputNumberOfSentences += 1;
        }
        return result.toString();
    }
}