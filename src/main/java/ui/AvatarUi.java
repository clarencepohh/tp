package ui;

public class AvatarUi {

    private static final String AVATAR = 
            "                ****#**+                \n" +
            "              #%%#++**####              \n" +
            "             %%###%%%##**#*             \n" +
            "             @%*        *%*             \n" +
            "             %@+=     =+#@=             \n" +
            "             #%+*** =***+%=             \n" +
            "             +#=    =   +#              \n" +
            "               *=  ++  ++               \n" +
            "               +*=+++==*                \n" +
            "               +#*====**                \n" +
            "              *==*###*= ++              \n" +
            "           +#%#    +=   =%%*=           \n" +
            "    *#**#%%%###   *#%=  =###%#%##**++   \n" +
            "###%%#######**#    %#   =#**########%#**\n" +
            "++%####**###**%   =##   *#**###***###%++\n" +
            "##%###########%#  *##=  #############%##\n" +
            " *%#%%##########+ #%%* *%#########%##%%#\n" +
            "+%%%%%%####%%#%%#=%%%#=#%%#%%#*#%%%%%%%=\n" +
            "*@%%%%@%%%#%%%%%%#%%%%##%%%%##%%%@%%%%% \n" +
            "#@%%%%@%%%%%%%%%%%%@@%%%%%%%%%%%%@%%%%@=\n";
    

    private static final String WELCOME_MESSAGE = "Hello there, I am CLI-nton, your CLI-based " + 
            "personal assistant in event management!";

    public static void printAvatar() {
        System.out.println(AVATAR);
    }

    public static void printWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }
}
