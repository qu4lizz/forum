import { useState } from "react";
import {
  CheckIcon,
  Combobox,
  Group,
  Input,
  InputBase,
  useCombobox,
} from "@mantine/core";
import { UpdateUserRequest } from "../../types/types";

interface Props {
  selected: string;
  topics?: any;
  updatedUser: UpdateUserRequest;
  setSelected: any;
}

export function ComboBoxTopic({ selected, topics, setSelected }: Props) {
  const combobox = useCombobox({
    onDropdownClose: () => combobox.resetSelectedOption(),
    onDropdownOpen: (eventSource) => {
      if (eventSource === "keyboard") {
        combobox.selectActiveOption();
      } else {
        combobox.updateSelectedOptionIndex("active");
      }
    },
  });

  const [value, setValue] = useState<string>(selected);

  const options = topics.map((topic: any) => (
    <Combobox.Option
      value={topic.name}
      key={topic.name}
      active={topic.name === value}
    >
      <Group gap="xs">
        {topic.name === value && <CheckIcon size={12} />}
        <span>{topic.name}</span>
      </Group>
    </Combobox.Option>
  ));

  return (
    <Combobox
      store={combobox}
      width="40%"
      resetSelectionOnOptionHover
      withinPortal={false}
      onOptionSubmit={(val) => {
        setSelected(val);
        setValue(val);
        combobox.updateSelectedOptionIndex("active");
      }}
    >
      <Combobox.Target targetType="button">
        <InputBase
          component="button"
          type="button"
          pointer
          rightSection={<Combobox.Chevron />}
          rightSectionPointerEvents="none"
          onClick={() => combobox.toggleDropdown()}
        >
          {value || <Input.Placeholder>Pick value</Input.Placeholder>}
        </InputBase>
      </Combobox.Target>

      <Combobox.Dropdown>
        <Combobox.Options>{options}</Combobox.Options>
      </Combobox.Dropdown>
    </Combobox>
  );
}
