package fluidapi;

import stolenfromfablabs.Fraction;

import net.minecraft.util.Identifier;

public class VirtualFluid {
    public final Identifier type;
    public final Fraction amount;

    public VirtualFluid(Identifier type, Fraction amount) {
        this.type = type;
        this.amount = amount;
    }
}