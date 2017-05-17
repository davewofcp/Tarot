/* TCL.java - Interactive Tarot deck, extension of Tarot class
 * Usage: java TCL
 * ------------------------------------------------------------------------
 *  Copyright (c) 2017 Dave Wollyung, Clifton Park, NY, United States
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TCL extends Tarot {
	public static void main(String[] args) throws NumberFormatException {
		random.setSeed(System.currentTimeMillis()); // magic
		init();
		
		System.out.println("Interactive Tarot Deck v1.0 by Dave Wollyung");
		System.out.println("Commands:");
		System.out.println("ask <question> - Seeds the algorithm with the given input");
		System.out.println("draw [n] - Draws a card, or n cards if specified");
		System.out.println("shuffle - Returns all cards to the deck and shuffles");
		System.out.println("rev [on|off] - Toggle card reversals, off by default");
		System.out.println("quit - Quits the program");
		System.out.print("> ");
		
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		
		String line;
		try {
			while ((line = buffer.readLine()) != null) {
				String[] tokens = line.split("\\s+");
				if (tokens.length == 0) return;
				
				switch (tokens[0]) {
					case "ask":
						if (line.length() < 5) break;
						String question = line.substring(4,line.length());
						random.setSeed(Long.valueOf(question.hashCode()));
						break;
					case "draw":
						int cards = 1;
						if (tokens.length == 2) {
							cards = Integer.parseInt(tokens[1]);
							
							if (cards < 1) {
								System.out.println("Must draw at least one card.");
								break;
							}
							if (cards > deck.size()) {
								System.out.println("The deck only has "+deck.size()+" cards in it.");
								break;
							}
						}
						
						ArrayList<Card> chosen = new ArrayList<Card>();
							
						for (int i = 0; i < cards; i++) {
							int idx = random.nextInt(deck.size());
							Card card = deck.get(idx);
							if (ENABLE_REVERSALS) {
								int rev = random.nextInt(2);
								if (rev > 0) card.setReversed(true);
							}
							chosen.add(card);
							deck.remove(idx);
						}
							
						System.out.println("Your card"+(cards > 1 ? "s":"")+":");
						for (Card card : chosen) {
							String rev = (ENABLE_REVERSALS && card.isReversed() ? ", reversed" : "");
							System.out.println(card.getName()+rev+" "+ BASE_URL+card.getUrl());
						}
						
						break;
					case "shuffle":
						deck.clear();
						init();
						random.setSeed(System.currentTimeMillis());
						System.out.println("Deck shuffled.");
						break;
					case "rev":
						if (tokens.length < 2) break;
						if (tokens[1].equals("on")) {
							ENABLE_REVERSALS = true;
							System.out.println("Reversals enabled.");
						}
						if (tokens[1].equals("off")) {
							ENABLE_REVERSALS = false;
							System.out.println("Reversals disabled.");
						}
						break;
					case "quit":
						return;
					default:
						break;
				}
				System.out.print("> ");
			}
		} catch (IOException e) {
			return;
		}
		return;
	}
}
