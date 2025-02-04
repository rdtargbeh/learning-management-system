package learning_management_system.backend.utility;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommentUserMention {

    /**
     * Extracts mentioned usernames from a given text content.
     *
     * @param content the text content.
     * @return a list of mentioned usernames.
     */
    public static List<String> extractMentionsFromContent(String content) {
        if (content == null || content.isEmpty()) {
            return Collections.emptyList();
        }

        // Regular expression to match mentions (e.g., @username)
        Pattern mentionPattern = Pattern.compile("@(\\w+)");
        Matcher matcher = mentionPattern.matcher(content);

        List<String> mentions = new ArrayList<>();
        while (matcher.find()) {
            mentions.add(matcher.group(1)); // Add the username without the @ symbol
        }
        return mentions;
    }
}
