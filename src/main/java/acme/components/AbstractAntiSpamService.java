
package acme.components;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.AbstractObject;
import acme.client.data.AbstractRole;
import acme.client.services.AbstractService;

@Service
public abstract class AbstractAntiSpamService<R extends AbstractRole, O extends AbstractObject> extends AbstractService<R, O> {

	@Autowired
	private AntiSpamSystemRepository repository;


	public void validateSpam(final O object) {
		Field[] fields = object.getClass().getDeclaredFields();
		boolean status = true;
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getType() == String.class)
				try {
					status = this.validateSpam(Objects.toString(field.get(object)));
					super.state(status, field.getName(), "spam.error.message");
					if (!status)
						break;
				} catch (Exception e) {
					e.printStackTrace();
				}

		}
	}

	private boolean validateSpam(final String text) {
		List<String> phrases = this.repository.findAllSpam();
		Integer numWords = text.split("\\\s+").length;
		double threshold = this.repository.findThresholdSpam();
		int count = 0;

		String regex;
		Pattern pattern;
		Matcher matcher;

		for (int i = 0; i < phrases.size(); i++) {
			String phrase = phrases.get(i).replaceAll("\\s+", "\\\\s+");
			StringBuilder regexBuilder = new StringBuilder("\\b(");
			regexBuilder.append(phrase);
			regexBuilder.append(")\\b");
			regex = regexBuilder.toString();
			pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
			matcher = pattern.matcher(text);

			while (matcher.find())
				count++;
		}

		double ratio = count / (double) numWords;

		return ratio <= threshold;
	}
}
