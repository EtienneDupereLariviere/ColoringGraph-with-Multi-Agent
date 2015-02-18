package ift605.tp3.commons;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Color implements Serializable{
	YELLOW, GREEN, BLUE;

	private static final List<Color> colorList  = Collections.unmodifiableList(Arrays.asList(values()));
	private static final Random random = new Random();

	public static Color getRandomColor()  {
		return colorList.get(random.nextInt(colorList.size()));
	}


	public static String toString(Color color) {
		if(color.equals(Color.GREEN)){
			return "vert";
		}else if(color.equals(Color.BLUE)){
			return "bleu";
		}else if(color.equals(Color.YELLOW)){
			return "jaune";
		}
		return null;
	}
}
