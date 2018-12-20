package cn.edu.bit.cs.util;
import java.util.function.Function;

import javafx.scene.control.TableRow;
import javafx.scene.control.Tooltip;

public class TooltipTableRow<T> extends TableRow<T> {

	  private Function<T, String> toolTipStringFunction;

	  public TooltipTableRow(Function<T, String> toolTipStringFunction) {
	    this.toolTipStringFunction = toolTipStringFunction;
	  }

	  @Override
	  protected void updateItem(T item, boolean empty) {
	    super.updateItem(item, empty);
	    if(item == null) {
	      setTooltip(null);
	    } else {
	      Tooltip tooltip = new Tooltip(toolTipStringFunction.apply(item));
	      setTooltip(tooltip);
	    }
	  }
	}
//toopltip ∑∫–Õ ”Îrow∞Û∂®