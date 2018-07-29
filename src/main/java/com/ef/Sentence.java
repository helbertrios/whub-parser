package com.ef;

import java.util.Random;

public class Sentence {
	
	private static final String[] SENTENCES = {	
		"You are responsible for your own happiness. If you expect others to make you happy, you will always be disappointed.                                                                                    ",
		"Working towards success will make you a master, but working towards satisfaction makes you a legend.                                                                                                    ",
		"It’s not what you do; it's how you do it! It’s not what you see; it’s how you look at it! It’s not how your life is; it’s how you live it.                                                              ",
		"Life is full of beauty. Notice it. Notice the bumblebee, the small child, and the smiling faces. Smell the rain, and feel the wind. Live your life to the fullest potential, and fight for your dreams. ",       
		"One of the simplest ways to stay happy: just let go of the things that make you sad.                                                                                                                    ",
		"Just because it's not what you were expecting doesn't mean it's not everything you've been waiting for.                                                                                                 ",
		"The best things in life are unexpected because there were no expectations.                                                                                                                              ",
		"If you don't know where you are going, any road will get you there. - Lewis Carroll                                                                                                                     ",
		"Life is like a piano... what you get out of it depends on how you play it.                                                                                                                              ",
		"The most important things in life aren't things. - Anthony J.D'Angelo                                                                                                                                   ",
		"Words can never replace feelings.                                                                                                                                                                       ",
		"If you want happiness for a day, go fishing.                                                                                                                                                            ",
		"If you want happiness for a month, get married.                                                                                                                                                         ",
		"If you want happiness for a year, inherit a fortune.                                                                                                                                                    ",
		"If you want happiness for a lifetime, help someone else. - Chinese Proverb                                                                                                                              ",
		"In the hopes of reaching the moon, men fail to see the flowers that blossom at their feet. - Albert Schweitzer                                                                                          ",
		"People with many interests live, not only longest, but happiest. - George Matthew Allen                                                                                                                 ",
		"A difference in your life today will start when you choose to move on from what happened yesterday.                                                                                                     ",
		"Our truest life is when we are in dreams awake. - Henry David Thoreau                                                                                                                                   ",
		"A time comes when you realize who matters, who doesn’t, and who never will. Don't worry about people from your past; there is a reason why they are not in your future!                                 ",
		"The happiest people don't have the best of everything. They just make the best of everything. Love generously; care deeply; speak kindly; leave the rest to God.                                        ",
		"Training yourself to live in the present, without regretting the past or fearing the future, is a recipe for a happy life.                                                                              ",
		"It's true that we don't know what we've got until we lose it, but it's also true that we don't know what we've been missing until it arrives.                                                           ",
		"Being happy doesn't mean that everything is perfect. It means that you've decided to look beyond the imperfections.                                                                                     ",
		"We tend to forget that happiness doesn't come as a result of getting something we don't have, but rather of recognizing and appreciating what we do have.                                               ",
		"People are just about as happy as they make up their minds to be. - Abraham Lincoln        																											 "
	};
	
	public static String getSentence() {
		try {
			String result = SENTENCES[getRandonNumber(0, (SENTENCES.length-1))];
			return result;
		} catch (Exception e) {
			return SENTENCES[0];
		}
	}
	
	private static int getRandonNumber(final int low, final int high) {
		Random random = new Random();
		int result = random.nextInt(high-low) + low;
		return result;
		
	}

}
