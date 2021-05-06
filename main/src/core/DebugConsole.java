package core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DebugConsole {

	class Message {

		final String label;
		final Supplier<Object> whatToPrint;

		public Message(String label, Supplier<Object> whatToPrint) {
			this.label = label;
			this.whatToPrint = whatToPrint;
		}

		public String format() {
			return label + whatToPrint.get().toString();
		}
	}

	private final List<Message> messages;

	public DebugConsole() {
		messages = new ArrayList<>();
	}

	public void addPrintable(String label, Supplier<Object> whatToPrint) {
		this.messages.add(new Message(label, whatToPrint));
	}

	public List<String> getPrintableMessages() {
		return messages.stream().map(Message::format).collect(Collectors.toList());
	}
}
