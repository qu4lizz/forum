import { useState } from "react";
import {
  CheckIcon,
  Combobox,
  Group,
  Input,
  InputBase,
  useCombobox,
} from "@mantine/core";
import { Role, Status } from "../../types/types";

interface Props {
  selected: string;
  type: "ROLE" | "STATUS";
  updatedUser: any;
}

export function ComboBox({ selected, type, updatedUser }: Props) {
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

  const options = (
    type === "ROLE" ? Object.values(Role) : Object.values(Status)
  ).map((item: any) => (
    <Combobox.Option value={item} key={item} active={item === value}>
      <Group gap="xs">
        {item === value && <CheckIcon size={12} />}
        <span>{item}</span>
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
        type === "ROLE" ? (updatedUser.role = val) : (updatedUser.status = val);
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
