## Language Checker


### I. Documentation:


#### Description:

It is really important to have a consistent grammar and correct spelling of a written text.

WordCheck is a standalone software application for Desktop and Android that helps in checking the spelling and grammatical correctness of the entered text.

It uses natural language processing (Ngrams) algorithm and grammatical rules along with having a built-in dictionary to identify mistakes and other language errors.


#### Done By:

Prateek Mehta - pmehta59@bu.edu



#### Implementation:

The model is based on Ngrams and a spell checker. In this method, the language is assumed to have a version of Markovian property which means that next word is highly correlated with some number of preceding words. In the project, 4 grams is used (every next word is evaluated depending on three preceding words). Briefly, the algorithm is trained with many text files and the resulting model and spell checker are stored as .ser files. When the application runs, the model and spell checker are being loaded and the algorithm just checks if a word is valid through a spell checker and it accepts if the previous 3 words produce this word in the model.

At the start of the model, our group decided to use a Ngram matrix (That is a 2D matrix of integers) where each row index is mapped to all 3 consecutive words and each column index is mapped to all words in the training set using hashmaps. The value corresponds to the number of times that the word specified by column index is preceded by three words specified by row index. However, this is not feasible because not all 3 preceding words produces all other words. The matrix has too many 0 values and the memory is really huge.

As a second idea, the model is constructed with HashMap data structure. Basically, ```HashMap <String, HashMap<String, Integer>> ```is the underlying structure. The first string is the three words concatenated with a space in between. Each consecutive three words is mapped to a hashmap in which keys are potential next word strings and values are the number of times these 4-word combination occurs.

As a third idea, Trie data structure is implemented due to its popular usage in string storage. The model stores each consecutive 4 words and the value when the combination ends is the number of occurrences. In Trie implementation, a TrieNode has an array of TrieNode (28 of them because there are 26 letters in the alphabet, prime (‘), and space ( ) are added), and a value.The implementation storage size is compared with the previous implementation. However, Trie takes almost 10 times more than HashMap implementation. As an improvement, TrieNode is changed to have ```HashMap<Character, TrieNode> ```instead of an array to decrease memory requirements. This improvement didn’t help much so HashMap structure is used.

After deciding on ```HashMap <String, HashMap<String, Integer>>``` structure, the model storage size is further decreased by ignoring the 4-word combinations whose value is less than 3. These combinations correspond to the 4 word that occurred 1 or 2 times. This greatly reduced model’s size. Currently, the model is trained on around 2 GB dataset, and trained model is uploaded.

Spell checker is stored as ```HashSet<String>``` structure which is thought to be the optimal way.

#### Features:

1. **An N-gram model for checking the correctness of language.**
&nbsp;  - For N-gram model: We use hash maps as the data structure which is explained in detail in previous chapter. Here, 4 grams is used (every next word is evaluated depending on three preceding words).

2. **Crawler that stores information about at least 10GB of ASCII text on the Internet.**
&nbsp;  - The crawler is able to download any size of twitter data based on a particular username or from its tweets database.
&nbsp;  - The crawler can also google search a particular string and download text from any number of resulting web pages.

3. **Checker that is able to assign confidence to the various language structures in a sample English text, and identify unusual outliers.**
&nbsp;  - After employing Ngrams and spell checker, we determine whether a word is acceptable or not.

4. **Evidence of the reasonable correctness of the checker (say, from a comparison to third-party tools).**
&nbsp;  - After comparing with big softwares like grammarly, lanuagetool. Our tool’s performance is up to the mark. Unlike those softwares, our tool is much lighter and free. However, the model can be furthermore improved.

5. **Command-line User Interface that allows users to check a given line or file, and output the most suspicious phrases.**

6. **Ability to receive speech input for checking.**
&nbsp;  - We use the Google Speech API to convert the speech to text and then pass it as a string for testing.

7. **Graphical User Interface that highlights suspicious textual elements from various input methods.**

8. **Develop and Android client for your checker: It has been implemented for multiple languages (English and Spanish). We have two main parts in the Android client.**
&nbsp;  - Language Checker through Speech: Used Google Speech API to convert speech to text and then the message is passed for parsing and then tested using the Ngram model.
&nbsp;  - Language Checker through text: Here we have two options to choose from English or Spanish, to enable the Spanish wordcheck just load the respective processed ngram word file in the asset folder of Android Studio project . After loading, any typed text could be tested.
&nbsp;  - The application was tested and modified such that it will not crash if the entered text is other than alphanumeric characters or even if no string is entered.

9. **Ability to provide feedback on a language that in which none of the team members have fluency.**
&nbsp; - Implemented it for Spanish Language: Similar to the way how Ngrams for English language was implemented, here the major difference lies in the text parsing as the Spanish language consists accent marks (~, `) making the characters different from simple alphanumeric characters. After this step the training and testing of the models remains as the same for the English Language.

#### References:

1.  [D. Jurafsky and J. Martin, Speech and language processing.](https://web.stanford.edu/~jurafsky/slp3/) Dorling Kindersley Pvt, Ltd., 2014, pp. Chapter.3(Language modeling using N-grams), Chapter.12(Syntactic processing), Chapter.22(Semantic Role Labelling and Argument Structure).

2. [A. Ratnaparkhi, "Maximum Entropy Model for Part-Of-Speech Tagging", Encyclopedia of Machine Learning 2010, Springer : 647-651](https://pdfs.semanticscholar.org/a574/e320d899e7e82e341eb64baef7dfe8a24642.pdf?_ga=2.29942586.460200632.1507759894-1835420224.1507759894)

3. [J. Nivre and M. Schloz, "Deterministic Dependency Parsing of English Text", COLING '04 Proceedings of the 20th international conference on Computational Linguistics, 2004](https://www.researchgate.net/publication/228707544_Deterministic_dependency_parsing_of_English_text)
4. [M. Najafabadi, F. Villanustre, T. Khoshgoftaar, N. Seliya, R. Wald and E. Muharemagic, "Deep learning applications and challenges in big data analytics", Journal of Big Data, vol. 2, no. 1, 2015.](https://journalofbigdata.springeropen.com/articles/10.1186/s40537-014-0007-7) Springer.

5. [Y. Bengio, R. Ducharme, and P. Vincent, "Neural Probabilistic Language model", 2017.](http://www.jmlr.org/papers/volume3/bengio03a/bengio03a.pdf) Journal of Machine Learning Research 3 (2003) 1137–1155

5. For Books Dataset - https://www.gutenberg.org/

5. Android Project - https://developer.android.com/training/basics/firstapp/creating-project.html
6. For the English words dictionary - https://github.com/dwyl/english-words/blob/master/words.txt

7. For the Spanish words dictionary - http://www.gwicks.net/dictionaries.htm

### II. Codes:

All codes are added in BitBucket. The reference list contains the links used for the project.
The testing codes are also in the project.



Prateek Mehta








