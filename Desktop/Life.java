import java.util.Random;

public class Life{

	static int age;
	static String name, sex, gender, birthday;
	static boolean isWorthIt = true, isNotWorthIt = false;
	static Random rand = new Random();

	public Life(String birth, String birthName){
		birthday = birth;
		age = 0;
		sex = rand.nextInt() % 2 == 1? "Male" : "Female";
		name = birthName;
	}

	public static void main(String[] args){
		Life yourLife = new Life("Oct 30, 1998", "Miguel Madridejos");
		boolean youHaveARealization = false, youAreAlive = true;
		LifeLoop:
		while(youAreAlive){
			youHaveARealization = rand.nextBoolean();
			if (youHaveARealization){
				yourLife.epiphany();
			}
			System.out.println(yourLife.isWorthIt?"Good for you!":"GG, screw it.");
			if(yourLife.isNotWorthIt){
				System.out.println("What's the point?");
				end(yourLife);
				break LifeLoop;
			}
			if(yourLife.age > 80){
				System.out.println("I'm too old for this crap.");
				end(yourLife);
				break LifeLoop;
			}
			yourLife.birthday();
		}
	}

	private static void end(Life life){
		System.out.println("You died at " + life.age + ". Good Job.");
	}

	private static void epiphany(){
		isWorthIt = false;
		isNotWorthIt = true;
	}

	private static void birthday(){
		age++;
	}
}
