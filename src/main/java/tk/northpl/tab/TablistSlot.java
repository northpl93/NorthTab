package tk.northpl.tab;

import java.util.ArrayList;
import java.util.List;

import com.comphenix.protocol.wrappers.WrappedGameProfile;

public final class TablistSlot
{
    private final WrappedGameProfile     virtualPlayer;
    private       String                 skinNick;
    private       boolean                isSkinNickDirty;
    private       String                 globalText;
    private       boolean                isGlobalTextDirty;
    private final List<CustomPlayerText> customPlayersTexts;

    public TablistSlot(final WrappedGameProfile virtualPlayer, final String skinNick, final String globalText)
    {
        this.virtualPlayer = virtualPlayer;
        this.skinNick = skinNick;
        this.globalText = globalText;

        this.customPlayersTexts = new ArrayList<>(0);
    }

    void setSkinNick(final String skinNick)
    {
        if (! this.skinNick.equals(skinNick))
        {
            this.isSkinNickDirty = true;
        }
        this.skinNick = skinNick;
        this.virtualPlayer.getProperties().removeAll("textures");
        //noinspection ConstantConditions
        this.virtualPlayer.getProperties().putAll("textures", Utils.getSkinProperties(skinNick));
    }

    void setGlobalText(final String globalText)
    {
        if (! this.globalText.equals(globalText))
        {
            this.isGlobalTextDirty = true;
        }
        this.globalText = globalText;
    }

    void setIsSkinNickDirty(final boolean isSkinNickDirty)
    {
        this.isSkinNickDirty = isSkinNickDirty;
    }

    void setIsGlobalTextDirty(final boolean isGlobalTextDirty)
    {
        this.isGlobalTextDirty = isGlobalTextDirty;
    }

    public String getSkinNick()
    {
        return this.skinNick;
    }

    public WrappedGameProfile getVirtualPlayer()
    {
        return this.virtualPlayer;
    }

    public String getGlobalText()
    {
        return this.globalText;
    }

    public List<CustomPlayerText> getCustomPlayersTexts()
    {
        return this.customPlayersTexts;
    }

    public boolean isGlobalTextDirty()
    {
        return this.isGlobalTextDirty;
    }

    public boolean isSkinNickDirty()
    {
        return this.isSkinNickDirty;
    }

    public String getTextForPlayer(final String player)
    {
        for (final CustomPlayerText ctt : this.customPlayersTexts)
        {
            if (ctt.getPlayerNick().equals(player))
            {
                return ctt.getText();
            }
        }
        return this.globalText;
    }

    public void setForPlayer(final String player, final String text)
    {
        for (final CustomPlayerText ctt : this.customPlayersTexts)
        {
            if (ctt.getPlayerNick().equals(player))
            {
                ctt.setText(text);
                if (! ctt.getText().equals(text))
                {
                    ctt.setDirty(true);
                }
                return;
            }
        }
        this.customPlayersTexts.add(new CustomPlayerText(player, text));
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("TablistSlot{");
        sb.append("virtualPlayer=").append(this.virtualPlayer);
        sb.append(", skinNick='").append(this.skinNick).append('\'');
        sb.append(", isSkinNickDirty=").append(this.isSkinNickDirty);
        sb.append(", globalText='").append(this.globalText).append('\'');
        sb.append(", isGlobalTextDirty=").append(this.isGlobalTextDirty);
        sb.append(", customPlayersTexts=").append(this.customPlayersTexts);
        sb.append('}');
        return sb.toString();
    }

    public static final class CustomPlayerText
    {
        private final String  playerNick;
        private       String  text;
        private       boolean isDirty;

        public CustomPlayerText(final String playerNick, final String text)
        {
            this.playerNick = playerNick;
            this.text = text;

            this.isDirty = true;
        }

        public String getPlayerNick()
        {
            return this.playerNick;
        }

        public String getText()
        {
            return this.text;
        }

        public boolean isDirty()
        {
            return this.isDirty;
        }

        void setText(final String text)
        {
            this.text = text;
        }

        void setDirty(final boolean dirty)
        {
            this.isDirty = dirty;
        }

        @Override
        public String toString()
        {
            final StringBuilder sb = new StringBuilder("CustomPlayerText{");
            sb.append("playerNick='").append(this.playerNick).append('\'');
            sb.append(", text='").append(this.text).append('\'');
            sb.append(", isDirty=").append(this.isDirty);
            sb.append('}');
            return sb.toString();
        }
    }
}