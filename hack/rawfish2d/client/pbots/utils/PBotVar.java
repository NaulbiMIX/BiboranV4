package hack.rawfish2d.client.pbots.utils;

import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.modules.ModuleBase;

public class PBotVar {
	private VarType type;
	private int i_var;
	private boolean b_var;
	private double d_var;
	private String s_var;
	private String modulename;
	private String varname;
	
	public PBotVar(String modulename, String varname, int value) {
		this.modulename = modulename;
		this.varname = varname;
		this.i_var = value;
		this.type = VarType.INT;
		PBotCmd.pbot_vars.add(this);
	}
	
	public PBotVar(String modulename, String varname, boolean value) {
		this.modulename = modulename;
		this.varname = varname;
		this.b_var = value;
		this.type = VarType.BOOLEAN;
		PBotCmd.pbot_vars.add(this);
	}
	
	public PBotVar(String modulename, String varname, double value) {
		this.modulename = modulename;
		this.varname = varname;
		this.d_var = value;
		this.type = VarType.DOUBLE;
		PBotCmd.pbot_vars.add(this);
	}
	
	public PBotVar(String modulename, String varname, String value) {
		this.modulename = modulename;
		this.varname = varname;
		this.s_var = value;
		this.type = VarType.STRING;
		PBotCmd.pbot_vars.add(this);
	}
	
	public void setInt(int par) {
		this.i_var = par;
	}

	public void setBool(boolean par) {
		this.b_var = par;
	}

	public void setDouble(double par) {
		this.d_var = par;
	}

	public void setString(String par) {
		this.s_var = par;
	}
	
	public int get(int par) {
		return this.i_var;
	}
	
	public boolean get(boolean par) {
		return this.b_var;
	}
	
	public double get(double par) {
		return this.d_var;
	}
	
	public String get(String par) {
		return this.s_var;
	}
	
	public String getModuleName() {
		return this.modulename;
	}
	
	public String getVarName() {
		return this.varname;
	}
	
	public VarType getType() {
		return this.type;
	}
}
