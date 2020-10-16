package code;

public class AdvancedWord extends Word{
    private String wordPronounce;
    private String wordType;

    public void setWordPronounce(String wordPronounce) {
        this.wordPronounce = wordPronounce;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public String getWordPronounce() {
        return wordPronounce;
    }

    public String getWordType() {
        return wordType;
    }

    public AdvancedWord() {

    }

    public AdvancedWord(String wordTarget, String wordPronounce, String wordType, String wordExplain) {
        super(wordTarget, wordExplain);
        this.wordPronounce = wordPronounce;
        this.wordType = wordType;
    }

    @Override
    public String toString() {
        return "AdvancedWord[" + super.toString() +
                "wordPronounce: " + wordPronounce +
                ", wordType: " + wordType + ']';
    }
}
