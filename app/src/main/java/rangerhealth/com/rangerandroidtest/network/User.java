package rangerhealth.com.rangerandroidtest.network;

import java.util.Random;

/**
 * Created by carlos on 10/28/17.
 */

public class User {

    private String[] eyes = {"eyes1", "eyes2", "eyes3", "eyes4", "eyes5", "eyes6", "eyes7", "eyes8", "eyes9"};
    private String[] noses = {"nose1", "nose2", "nose3", "nose4", "nose5", "nose6", "nose7", "nose8", "nose9"};
    private String[] mouths = {"mouth1", "mouth2", "mouth3", "mouth4", "mouth5", "mouth6", "mouth7", "mouth8", "mouth9"};

    public String name;
    public String avatar;

    public User(String name) {
        this.name = name;
        this.avatar = setRandomImage();
    }

    private String setRandomImage () {
        Random generator = new Random();
        int randomEye = generator.nextInt(eyes.length);
        int randomNose = generator.nextInt(noses.length);
        int randomMouth = generator.nextInt(mouths.length);

        return  "https://api.adorable.io/avatars/face/" + eyes[randomEye] + "/" + noses[randomNose] + "/" + mouths[randomMouth] + "/ffff66";
    }

    @Override
    public boolean equals(Object obj) {

        User otherUser = (User)obj;
        return (this.name.equals(otherUser.name) && this.avatar.equals(otherUser.avatar)) ;
    }
}
