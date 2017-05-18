/* Tarot.java - Draw Tarot cards from the command line
 * Usage: java Tarot <optional # of cards>
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

import java.util.ArrayList;
import java.util.Random;

public class Tarot {
	protected static boolean ENABLE_REVERSALS = false;
	protected static final int DEFAULT_N_CARDS_TO_DRAW = 5;
	
	protected static final String BASE_URL = "http://www.sacred-texts.com/tarot/pkt/";
	protected static ArrayList<Card> deck = new ArrayList<Card>();
	protected static Random random = new Random();
	
	public static void main(String[] args) {
		random.setSeed(System.currentTimeMillis()); // magic
		init();
		
		int cards = DEFAULT_N_CARDS_TO_DRAW;
		if (args.length > 0) {
			try {
				cards = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.out.println("That's not a number.");
				return;
			}
			if (cards < 1) {
				System.out.println("Must draw at least one card.");
				return;
			}
			if (cards > deck.size()) {
				System.out.println("The deck only has "+deck.size()+" cards in it.");
				return;
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
	}
	
	protected static void init() {
		// Major Arcana
		deck.add(new Card("The Fool","pktar00.htm"));
		deck.add(new Card("The Magician","pktar01.htm"));
		deck.add(new Card("The High Priestess","pktar02.htm"));
		deck.add(new Card("The Empress","pktar03.htm"));
		deck.add(new Card("The Emperor","pktar04.htm"));
		deck.add(new Card("The Hierophant","pktar05.htm"));
		deck.add(new Card("The Lovers","pktar06.htm"));
		deck.add(new Card("The Chariot","pktar07.htm"));
		deck.add(new Card("Strength","pktar08.htm"));
		deck.add(new Card("The Hermit","pktar09.htm"));
		deck.add(new Card("Wheel of Fortune","pktar10.htm"));
		deck.add(new Card("Justice","pktar11.htm"));
		deck.add(new Card("The Hanged Man","pktar12.htm"));
		deck.add(new Card("Death","pktar13.htm"));
		deck.add(new Card("Temperance","pktar14.htm"));
		deck.add(new Card("The Devil","pktar15.htm"));
		deck.add(new Card("The Tower","pktar16.htm"));
		deck.add(new Card("The Star","pktar17.htm"));
		deck.add(new Card("The Moon","pktar18.htm"));
		deck.add(new Card("The Sun","pktar19.htm"));
		deck.add(new Card("The Last Judgement","pktar20.htm"));
		deck.add(new Card("The World","pktar21.htm"));
		
		// Minor Arcana
		deck.add(new Card("King of Wands","pktwaki.htm"));
		deck.add(new Card("Queen of Wands","pktwaqu.htm"));
		deck.add(new Card("Knight of Wands","pktwakn.htm"));
		deck.add(new Card("Page of Wands","pktwapa.htm"));
		deck.add(new Card("Ten of Wands","pktwa10.htm"));
		deck.add(new Card("Nine of Wands","pktwa09.htm"));
		deck.add(new Card("Eight of Wands","pktwa08.htm"));
		deck.add(new Card("Seven of Wands","pktwa07.htm"));
		deck.add(new Card("Six of Wands","pktwa06.htm"));
		deck.add(new Card("Five of Wands","pktwa05.htm"));
		deck.add(new Card("Four of Wands","pktwa04.htm"));
		deck.add(new Card("Three of Wands","pktwa03.htm"));
		deck.add(new Card("Two of Wands","pktwa02.htm"));
		deck.add(new Card("Ace of Wands","pktwaac.htm"));
		deck.add(new Card("King of Cups","pktcuki.htm"));
		deck.add(new Card("Queen of Cups","pktcuqu.htm"));
		deck.add(new Card("Knight of Cups","pktcukn.htm"));
		deck.add(new Card("Page of Cups","pktcupa.htm"));
		deck.add(new Card("Ten of Cups","pktcu10.htm"));
		deck.add(new Card("Nine of Cups","pktcu09.htm"));
		deck.add(new Card("Eight of Cups","pktcu08.htm"));
		deck.add(new Card("Seven of Cups","pktcu07.htm"));
		deck.add(new Card("Six of Cups","pktcu06.htm"));
		deck.add(new Card("Five of Cups","pktcu05.htm"));
		deck.add(new Card("Four of Cups","pktcu04.htm"));
		deck.add(new Card("Three of Cups","pktcu03.htm"));
		deck.add(new Card("Two of Cups","pktcu02.htm"));
		deck.add(new Card("Ace of Cups","pktcuac.htm"));
		deck.add(new Card("King of Swords","pktswki.htm"));
		deck.add(new Card("Queen of Swords","pktswqu.htm"));
		deck.add(new Card("Knight of Swords","pktswkn.htm"));
		deck.add(new Card("Page of Swords","pktswpa.htm"));
		deck.add(new Card("Ten of Swords","pktsw10.htm"));
		deck.add(new Card("Nine of Swords","pktsw09.htm"));
		deck.add(new Card("Eight of Swords","pktsw08.htm"));
		deck.add(new Card("Seven of Swords","pktsw07.htm"));
		deck.add(new Card("Six of Swords","pktsw06.htm"));
		deck.add(new Card("Five of Swords","pktsw05.htm"));
		deck.add(new Card("Four of Swords","pktsw04.htm"));
		deck.add(new Card("Three of Swords","pktsw03.htm"));
		deck.add(new Card("Two of Swords","pktsw02.htm"));
		deck.add(new Card("Ace of Swords","pktswac.htm"));
		deck.add(new Card("King of Pentacles","pktpeki.htm"));
		deck.add(new Card("Queen of Pentacles","pktpequ.htm"));
		deck.add(new Card("Knight of Pentacles","pktpekn.htm"));
		deck.add(new Card("Page of Pentacles","pktpepa.htm"));
		deck.add(new Card("Ten of Pentacles","pktpe10.htm"));
		deck.add(new Card("Nine of Pentacles","pktpe09.htm"));
		deck.add(new Card("Eight of Pentacles","pktpe08.htm"));
		deck.add(new Card("Seven of Pentacles","pktpe07.htm"));
		deck.add(new Card("Six of Pentacles","pktpe06.htm"));
		deck.add(new Card("Five of Pentacles","pktpe05.htm"));
		deck.add(new Card("Four of Pentacles","pktpe04.htm"));
		deck.add(new Card("Three of Pentacles","pktpe03.htm"));
		deck.add(new Card("Two of Pentacles","pktpe02.htm"));
		deck.add(new Card("Ace of Pentacles","pktpeac.htm"));
	}
	
	public static class Card {
		private String name;
		private String url;
		private boolean isReversed = false;
		
		public Card(String name, String url) {
			this.name = name;
			this.url = url;
		}
		
		public String getName() {
			return name;
		}
		public String getUrl() {
			return url;
		}
		
		public void setReversed(boolean isReversed) {
			this.isReversed = isReversed;
		}
		public boolean isReversed() {
			return isReversed;
		}
	}
}
