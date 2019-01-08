package config;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class FrameConfig {
	
	private final String title;
	
	private final int windowUp;
	
	private final int width;
	
	private final int height;
	
	private final int padding;
	
	private final int border;
	
	private final int size_Rol;
	
	private final int loseIdx;
	
	
	
	/**
	 * 图层属性
	 */
	private final List<LayerConfig> layersConfig;
	
	private final ButtonConfig buttonConfig;
	
	@SuppressWarnings("unchecked")
	public FrameConfig(Element frame) {
		
		this.width = Integer.parseInt(frame.attributeValue("width"));
		this.height = Integer.parseInt(frame.attributeValue("height"));
		this.border = Integer.parseInt(frame.attributeValue("boder"));
		this.padding = Integer.parseInt(frame.attributeValue("padding"));
		//获取标题
		this.title = frame.attributeValue("title");
		//获取窗口拔高
		this.windowUp = Integer.parseInt(frame.attributeValue("windowUp"));
		this.size_Rol = Integer.parseInt(frame.attributeValue("size_Rol"));
		this.loseIdx = Integer.parseInt(frame.attributeValue("loseIdx"));
		
		List<Element> layers = frame.elements("Layer");
		layersConfig = new ArrayList<LayerConfig>();
		//  
		for (Element layer : layers) {
			LayerConfig Tempor = new LayerConfig(
					layer.attributeValue("className"),
					Integer.parseInt(layer.attributeValue("x")),
					Integer.parseInt(layer.attributeValue("y")),
					Integer.parseInt(layer.attributeValue("w")),
					Integer.parseInt(layer.attributeValue("h"))
					);
			
			layersConfig.add(Tempor);
		}
		buttonConfig = new ButtonConfig(frame.element("button"));
	}

	public String getTitle() {
		return title;
	}

	public int getWindowUp() {
		return windowUp;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getPadding() {
		return padding;
	}

	public int getBorder() {
		return border;
	}

	public List<LayerConfig> getLayersConfig() {
		return layersConfig;
	}

	public int getSize_Rol() {
		return size_Rol;
	}

	public int getLoseIdx() {
		return loseIdx;
	}

	public ButtonConfig getButtonConfig() {
		return buttonConfig;
	}
	
	
}
