/*
 * Define msgType for different kinds of message
 */
package data;

public enum msgType {
	REBIND,
	LOOKUP,
	LOOKUPFAIL,
	PASSVAL,
	PASSREF,
	LIST,
	INVOKE,
	REPLY,
	INVOKEERROR
}